import React,{useState,useEffect} from "react";

function UserList(){

const[users,setUsers]=useState([]);
const[loading,setLoading]=useState(true);
const[error,setError]=useState(null);

useEffect(()=>{
fetch("https://jsonplaceholder.typicode.com/users")
.then(res=>res.json())
.then(data=>{
setUsers(data);
setLoading(false);
})
.catch(()=>{
setError("Error fetching API");
setLoading(false);
});
},[]);

if(loading)return <h3>Loading...</h3>;
if(error)return <h3>{error}</h3>;

return(
<div>
<h2>Users API</h2>

{users.map(u=>(
<div key={u.id} className="card">
<h4>{u.name}</h4>
<p>{u.email}</p>
<p>{u.phone}</p>
</div>
))}

</div>
);
}

export default UserList;