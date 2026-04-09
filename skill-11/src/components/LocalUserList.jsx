import React,{useState,useEffect} from "react";

function LocalUserList(){

const[users,setUsers]=useState([]);
const[loading,setLoading]=useState(true);
const[error,setError]=useState(null);

useEffect(()=>{
fetch("/users.json")
.then(res=>res.json())
.then(data=>{
setUsers(data);
setLoading(false);
})
.catch(err=>{
setError("Error loading data");
setLoading(false);
});
},[]);

if(loading)return <h3>Loading...</h3>;
if(error)return <h3>{error}</h3>;

return(
<div>
<h2>Local Users</h2>

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

export default LocalUserList;