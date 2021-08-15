import React from "react";
import ApiAccessLog from "../shared/ApiAccessLog";

function AccessLog() {
    return (
        <div className="access">
            <h1>Access Logs</h1>

            <p>History access logs</p>
            <ApiAccessLog/>
        </div>
    );
}

export default AccessLog;
