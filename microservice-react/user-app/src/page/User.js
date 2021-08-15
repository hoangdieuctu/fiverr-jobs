import React from "react";
import ApiUser from "../shared/ApiUser";

function User() {
    return (
        <div className="user">
            <h1>User</h1>

            <p>List of users</p>
            <ApiUser/>
        </div>
    );
}

export default User;
