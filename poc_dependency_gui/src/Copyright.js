import React from "react";

class Copyright extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                generated from <a href="https://github.com/ehmkah/poc_dependecies">poc dependencies</a>, version: 0.0.3,
                Copyright Michael Krauße 2020
            </div>)
    }
}

export default Copyright;