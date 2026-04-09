import React,{useEffect,useState} from "react";
import axios from "axios";
import AddStudent from "./AddStudent";

function StudentList(){

const[students,setStudents]=useState([]);
const[editStudent,setEditStudent]=useState(null);

const fetchStudents=()=>{

axios.get("http://localhost:8085/students")
.then(res=>{
setStudents(res.data);
});

};

useEffect(()=>{
fetchStudents();
},[]);

const del=(id)=>{
axios.delete(`http://localhost:8085/students/${id}`)
.then(()=>{
fetchStudents();
});
};

return(

<div>

<AddStudent
fetchStudents={fetchStudents}
editStudent={editStudent}
setEditStudent={setEditStudent}
/>

<h2>Student List</h2>

<table border="1">

<thead>
<tr>
<th>Name</th>
<th>Email</th>
<th>Course</th>
<th>Action</th>
</tr>
</thead>

<tbody>

{students.map(s=>(
<tr key={s.id}>
<td>{s.name}</td>
<td>{s.email}</td>
<td>{s.course}</td>

<td>
<button onClick={()=>setEditStudent(s)}>Update</button>
<button onClick={()=>del(s.id)}>Delete</button>
</td>

</tr>
))}

</tbody>

</table>

</div>

);

}

export default StudentList;