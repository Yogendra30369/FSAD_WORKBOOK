import React,{useState,useEffect} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

function Profile(){

const [user,setUser]=useState({});
const navigate=useNavigate();

useEffect(()=>{

const id=localStorage.getItem("userId");

if(!id){
navigate("/login");
}

axios.get(`http://localhost:8080/user/${id}`)
.then(res=>{
setUser(res.data);
});

},[]);

return(

<div>

<h2>Profile</h2>

<p>Username: {user.username}</p>
<p>Email: {user.email}</p>

</div>

);

}

export default Profile;