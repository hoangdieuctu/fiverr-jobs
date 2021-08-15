import React, {useEffect, useState} from "react";

function GithubUser({username}) {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if(!username) return;
        setLoading(true);
        fetch('https://api.github.com/users/' + username)
            .then((response) => response.json())
            .then(setData)
            .then(() => setLoading(false));
    }, [username])

    if (loading) {
        return <p>Loading...</p>;
    }

    if(data) {
        return (
            <div className="user">
                <img alt={data.avatar_url} src={data.avatar_url} width={100}/>
                <p>{data.login}</p>
            </div>
        )
    }

    return <p>Cannot load data</p>
}

export default GithubUser;
