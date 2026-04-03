import React,{useState,useEffect} from "react";
import axios from "axios";

function AddStudent({fetchStudents,editStudent,setEditStudent}){

const[name,setName]=useState("");
const[email,setEmail]=useState("");
const[course,setCourse]=useState("");

useEffect(()=>{
if(editStudent){
setName(editStudent.name);
setEmail(editStudent.email);
setCourse(editStudent.course);
}
},[editStudent]);

const submit=(e)=>{
e.preventDefault();

const student={
name:name,
email:email,
course:course
};

if(editStudent){

axios.put(`http://localhost:8085/students/${editStudent.id}`,student)
.then(()=>{
fetchStudents();
setEditStudent(null);
});

}else{

axios.post("http://localhost:8085/students",student)
.then(()=>{
fetchStudents();
});

}

setName("");
setEmail("");
setCourse("");

};

return(

<div>

<h2>Add Student</h2>

<form onSubmit={submit}>

<input
placeholder="Name"
value={name}
onChange={e=>setName(e.target.value)}
/>

<input
placeholder="Email"
value={email}
onChange={e=>setEmail(e.target.value)}
/>

<input
placeholder="Course"
value={course}
onChange={e=>setCourse(e.target.value)}
/>

<button type="submit">Add</button>

</form>

</div>

);

}

export default AddStudent;