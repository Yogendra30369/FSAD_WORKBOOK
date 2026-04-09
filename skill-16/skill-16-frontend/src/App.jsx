import { useEffect, useState } from 'react'
import './App.css'

const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8080/students'

function App() {
  const [students, setStudents] = useState([])
  const [form, setForm] = useState({ name: '', email: '', course: '' })
  const [editingId, setEditingId] = useState(null)
  const [loading, setLoading] = useState(false)
  const [message, setMessage] = useState('')
  const [error, setError] = useState('')

  useEffect(() => {
    fetchStudents()
  }, [])

  const fetchStudents = async () => {
    setLoading(true)
    setError('')

    try {
      const response = await fetch(API_BASE)
      if (!response.ok) {
        throw new Error('Failed to fetch students')
      }

      const data = await response.json()
      setStudents(data)
    } catch (err) {
      setError(err.message || 'Unable to fetch students')
    } finally {
      setLoading(false)
    }
  }

  const handleInputChange = (event) => {
    const { name, value } = event.target
    setForm((prev) => ({ ...prev, [name]: value }))
  }

  const resetForm = () => {
    setForm({ name: '', email: '', course: '' })
    setEditingId(null)
  }

  const handleSubmit = async (event) => {
    event.preventDefault()
    setError('')
    setMessage('')

    const isEditing = editingId !== null
    const url = isEditing ? `${API_BASE}/${editingId}` : API_BASE
    const method = isEditing ? 'PUT' : 'POST'

    try {
      const response = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form),
      })

      if (!response.ok) {
        const errorPayload = await response.json()
        const validationMessage = errorPayload.validationErrors
          ? Object.values(errorPayload.validationErrors).join(', ')
          : errorPayload.message

        throw new Error(validationMessage || 'Request failed')
      }

      setMessage(isEditing ? 'Student updated successfully' : 'Student added successfully')
      resetForm()
      await fetchStudents()
    } catch (err) {
      setError(err.message || 'Unable to save student')
    }
  }

  const handleEdit = (student) => {
    setEditingId(student.id)
    setForm({ name: student.name, email: student.email, course: student.course })
    setMessage('')
    setError('')
  }

  const handleDelete = async (id) => {
    setError('')
    setMessage('')

    try {
      const response = await fetch(`${API_BASE}/${id}`, { method: 'DELETE' })
      if (!response.ok) {
        const errorPayload = await response.json()
        throw new Error(errorPayload.message || 'Delete failed')
      }

      setMessage('Student deleted successfully')
      await fetchStudents()
    } catch (err) {
      setError(err.message || 'Unable to delete student')
    }
  }

  return (
    <div className="app-shell">
      <header className="header">
        <h1>Student Management</h1>
        <p>Connected to Spring Boot backend APIs</p>
      </header>

      <section className="card form-card">
        <h2>{editingId ? 'Update Student' : 'Add Student'}</h2>
        <form onSubmit={handleSubmit} className="student-form">
          <label>
            Name
            <input
              type="text"
              name="name"
              value={form.name}
              onChange={handleInputChange}
              placeholder="Enter student name"
              required
            />
          </label>

          <label>
            Email
            <input
              type="email"
              name="email"
              value={form.email}
              onChange={handleInputChange}
              placeholder="Enter email"
              required
            />
          </label>

          <label>
            Course
            <input
              type="text"
              name="course"
              value={form.course}
              onChange={handleInputChange}
              placeholder="Enter course"
              required
            />
          </label>

          <div className="form-actions">
            <button type="submit">{editingId ? 'Update' : 'Add'}</button>
            {editingId && (
              <button type="button" className="secondary" onClick={resetForm}>
                Cancel
              </button>
            )}
          </div>
        </form>
      </section>

      {message && <p className="status success">{message}</p>}
      {error && <p className="status error">{error}</p>}

      <section className="card list-card">
        <div className="list-head">
          <h2>Students</h2>
          <button type="button" className="secondary" onClick={fetchStudents}>
            Refresh
          </button>
        </div>
        {loading ? (
          <p>Loading students...</p>
        ) : students.length === 0 ? (
          <p>No students found.</p>
        ) : (
          <div className="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Course</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {students.map((student) => (
                  <tr key={student.id}>
                    <td>{student.id}</td>
                    <td>{student.name}</td>
                    <td>{student.email}</td>
                    <td>{student.course}</td>
                    <td className="actions">
                      <button type="button" className="small" onClick={() => handleEdit(student)}>
                        Edit
                      </button>
                      <button type="button" className="small danger" onClick={() => handleDelete(student.id)}>
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </section>
    </div>
  )
}

export default App
