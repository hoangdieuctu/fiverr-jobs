import React from "react";
import {Link} from "react-router-dom";

function Menu() {
    const menu = [
        {
          "path": "/",
          "name": "Home"
        },
        {
            "path": "/user",
            "name": "User"
        },
        {
            "path": "/access",
            "name": "Access"
        },
        {
            "path": "/about",
            "name": "About"
        },
    ]

    return (
        <div className="menu">
            {
                menu.map((item, index) => (
                    <Link className="menu-item" key={index} to={item.path}>{item.name}</Link>
                ))
            }
        </div>
    );
}

export default Menu;
