import React from "react";

import content from "./data/data.json"

/**
 * Displays a simple overview for all artifacts.
 */
class Overview extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            keys: ["gradleVersion", "org.junit.jupiter:junit-jupiter", "com.nhaarman.mockitokotlin2:mockito-kotlin"],
            "content": content.content
        };

    }

    renderConsumer(element, displayKeys) {
        return displayKeys.map((keyValue) => <td>{element.directDependencies[keyValue]}</td>);
    }

    render() {
        const headline = this.state.keys.map((element) => <th>{element}</th>);
        const content = this.state.content.map((element) => <tr>
            <td>{element.projectName}</td>
            {this.renderConsumer(element, this.state.keys)}</tr>)
        return (
            <div>
                <table>
                    <thead>
                    <tr>
                        <th>consumer</th>
                        {headline}
                    </tr>
                    </thead>
                    <tbody>
                        {content}
                    </tbody>
                </table>
                <div>
                    generated from poc-dependencies, version: 0.0.1, Copyright Michael Krauße 2020
                </div>
            </div>

        );
    }

}

export default Overview;