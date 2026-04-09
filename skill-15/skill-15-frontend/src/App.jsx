import { useMemo, useState } from 'react'
import './App.css'

const API_BASE = 'http://localhost:8080'

function prettyJson(value) {
  if (typeof value === 'string') {
    return value
  }
  return JSON.stringify(value, null, 2)
}

async function parseResponse(response) {
  const raw = await response.text()
  let body = raw

  try {
    body = raw ? JSON.parse(raw) : {}
  } catch {
    body = raw
  }

  return {
    ok: response.ok,
    status: response.status,
    statusText: response.statusText,
    body,
  }
}

function App() {
  const [username, setUsername] = useState('admin')
  const [password, setPassword] = useState('admin123')
  const [employeeName, setEmployeeName] = useState('john')

  const [token, setToken] = useState('')
  const [role, setRole] = useState('')
  const [activeUser, setActiveUser] = useState('')

  const [result, setResult] = useState('Run an action to see API response here.')
  const [loading, setLoading] = useState(false)

  const hasToken = useMemo(() => token.trim().length > 0, [token])

  async function runRequest(path, options = {}, customToken = token) {
    setLoading(true)
    try {
      const headers = {
        ...(options.headers || {}),
      }

      if (customToken) {
        headers.Authorization = `Bearer ${customToken}`
      }

      const response = await fetch(`${API_BASE}${path}`, {
        ...options,
        headers,
      })

      const parsed = await parseResponse(response)
      setResult(
        prettyJson({
          request: { path, method: options.method || 'GET' },
          response: parsed,
        })
      )
      return parsed
    } catch (error) {
      const message = error instanceof Error ? error.message : String(error)
      setResult(prettyJson({ error: message }))
      return null
    } finally {
      setLoading(false)
    }
  }

  async function login() {
    const response = await runRequest('/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username, password }),
    }, '')

    if (response && response.ok && typeof response.body === 'object') {
      setToken(response.body.token || '')
      setRole(response.body.role || '')
      setActiveUser(response.body.username || '')
    }
  }

  function logout() {
    setToken('')
    setRole('')
    setActiveUser('')
    setResult('Token cleared. Endpoints now behave as unauthenticated.')
  }

  return (
    <main className="portal-shell">
      <header className="hero">
        <p className="tag">Skill 15 • JWT + RBAC</p>
        <h1>Corporate Access Console</h1>
        <p className="subtitle">
          Authenticate with JWT and verify role authorization for ADMIN and EMPLOYEE endpoints.
        </p>
      </header>

      <section className="panel login-panel">
        <h2>1. Login</h2>
        <div className="fields">
          <label>
            Username
            <input value={username} onChange={(e) => setUsername(e.target.value)} placeholder="admin or employee" />
          </label>
          <label>
            Password
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="admin123 or emp123" />
          </label>
        </div>
        <div className="actions">
          <button onClick={login} disabled={loading}>Login and Generate JWT</button>
          <button onClick={logout} className="ghost" disabled={loading}>Clear Token</button>
        </div>
        <p className="hint">Seeded users: admin/admin123 and employee/emp123</p>
      </section>

      <section className="panel token-panel">
        <h2>2. Active Session</h2>
        <div className="session-grid">
          <div>
            <span className="k">User</span>
            <span className="v">{activeUser || '-'}</span>
          </div>
          <div>
            <span className="k">Role</span>
            <span className="v">{role || '-'}</span>
          </div>
          <div>
            <span className="k">Token Present</span>
            <span className="v">{hasToken ? 'Yes' : 'No'}</span>
          </div>
        </div>
        <textarea
          className="token-box"
          value={token}
          onChange={(e) => setToken(e.target.value)}
          placeholder="JWT token appears here after login"
        />
      </section>

      <section className="panel endpoint-panel">
        <h2>3. Secured Endpoint Tests</h2>
        <div className="fields single">
          <label>
            Employee Name (for /admin/add and /admin/delete)
            <input value={employeeName} onChange={(e) => setEmployeeName(e.target.value)} />
          </label>
        </div>

        <div className="button-grid">
          <button onClick={() => runRequest('/employee/profile')} disabled={loading}>GET /employee/profile (with JWT)</button>
          <button className="ghost" onClick={() => runRequest('/employee/profile', {}, '')} disabled={loading}>GET /employee/profile (without JWT)</button>

          <button onClick={() => runRequest(`/admin/add?employeeName=${encodeURIComponent(employeeName)}`, { method: 'POST' })} disabled={loading}>POST /admin/add (with JWT)</button>
          <button className="ghost" onClick={() => runRequest(`/admin/add?employeeName=${encodeURIComponent(employeeName)}`, { method: 'POST' }, '')} disabled={loading}>POST /admin/add (without JWT)</button>

          <button onClick={() => runRequest(`/admin/delete?employeeName=${encodeURIComponent(employeeName)}`, { method: 'DELETE' })} disabled={loading}>DELETE /admin/delete (with JWT)</button>
          <button className="ghost" onClick={() => runRequest(`/admin/delete?employeeName=${encodeURIComponent(employeeName)}`, { method: 'DELETE' }, '')} disabled={loading}>DELETE /admin/delete (without JWT)</button>

          <button className="warn" onClick={() => runRequest('/employee/profile', {}, 'invalid.token.value')} disabled={loading}>GET /employee/profile (invalid JWT)</button>
        </div>
      </section>

      <section className="panel output-panel">
        <h2>4. API Response</h2>
        <pre>{result}</pre>
      </section>
    </main>
  )
}

export default App
