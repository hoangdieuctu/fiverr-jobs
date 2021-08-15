import React from "react";
import GithubUser from "../shared/GithubUser";

function About() {
    return (
        <div className="about">
            <h1>About</h1>

            <h3>Owner</h3>
            <GithubUser username="gregcousin126"/>

            <h3>Developer</h3>
            <GithubUser username="hoangdieuctu"/>
        </div>
    );
}

export default About;
