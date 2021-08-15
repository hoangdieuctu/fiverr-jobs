import React, {useEffect, useState} from "react";

function ApiUser() {
    const [users, setUsers] = useState(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);
        fetch('http://localhost:8080/api/users')
            .then((response) => response.json())
            .then(setUsers)
            .then(() => setLoading(false));
    }, [])

    if (loading) {
        return <p>Loading...</p>;
    }

    if(users) {
        return (
            <div className="user">
                <table>
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>First name</th>
                        <th>Last name</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        users.map((user, index) => (
                            <tr key={index}>
                                <td>{user.id}</td>
                                <td>{user.firstname}</td>
                                <td>{user.lastname}</td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </div>
        )
    }

    return <p>Cannot load data</p>
}

export default ApiUser;
