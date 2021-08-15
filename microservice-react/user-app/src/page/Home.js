import React from "react";
import {Link} from "react-router-dom";

function Home() {
    return (
        <div className="home">
            <h1>Home</h1>
            <p>The user app for loading data from backend service.</p>
            <p>Go to <Link key="user" to="/user">User</Link></p>
        </div>
    );
}

export default Home;
