import React,{useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

function Login(){

const [login,setLogin]=useState({
username:"",
password:""
});

const navigate=useNavigate();

const handleChange=(e)=>{
setLogin({...login,[e.target.name]:e.target.value});
};

const handleSubmit=(e)=>{
e.preventDefault();

axios.post("http://localhost:8080/login",login)
.then(res=>{

localStorage.setItem("userId",res.data.id);
navigate("/home");

})
.catch(()=>{
alert("Invalid Credentials");
});

};

return(

<div className="container">

<h2>Login</h2>

<form onSubmit={handleSubmit}>

<input name="username" placeholder="Username" onChange={handleChange}/>

<input name="password" type="password" placeholder="Password" onChange={handleChange}/>

<button>Login</button>

</form>

</div>

);

}

export default Login;