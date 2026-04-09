import React,{useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

function Register(){

const [user,setUser]=useState({
username:"",
email:"",
password:""
});

const navigate=useNavigate();

const handleChange=(e)=>{
setUser({...user,[e.target.name]:e.target.value});
};

const handleSubmit=(e)=>{
e.preventDefault();

axios.post("http://localhost:8080/register",user)
.then(()=>{
alert("Registered Successfully");
navigate("/login");
});
};

return(

<div className="container">

<h2>Register</h2>

<form onSubmit={handleSubmit}>

<input name="username" placeholder="Username" onChange={handleChange}/>

<input name="email" placeholder="Email" onChange={handleChange}/>

<input name="password" type="password" placeholder="Password" onChange={handleChange}/>

<button>Register</button>

</form>

</div>

);

}

export default Register;