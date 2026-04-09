import React,{useEffect} from "react";
import {useNavigate} from "react-router-dom";

function Home(){

const navigate=useNavigate();

useEffect(()=>{

const user=localStorage.getItem("userId");

if(!user){
navigate("/login");
}

},[]);

return(

<div>

<h2>Welcome Home Page</h2>

</div>

);

}

export default Home;