import React from "react";

/**
 * Displays a simple overview for all artifacts.
 */
class Overview extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            keys: ["gradleVersion", "org.junit.jupiter:junit-jupiter", "com.nhaarman.mockitokotlin2:mockito-kotlin"],
            "content": [
                {
                    "projectName": "imglib",
                    "version": "1.4.0",
                    "directDependencies": {
                        "gradleVersion": "6.3",
                        "org.junit.jupiter:junit-jupiter": "5.6.2",
                        "com.nhaarman.mockitokotlin2:mockito-kotlin": "2.2.0"
                    }
                }
            ]
        };

    }

    renderConsumer(element, displayKeys) {
      return displayKeys.map((keyValue) => <td>{element.directDependencies[keyValue]}</td>);
    }

    render() {
        const headline = this.state.keys.map((element) => <th>{element}</th>);
        const content = this.state.content.map((element) => <tr><td>{element.projectName}</td>{this.renderConsumer(element, this.state.keys)}</tr>)
        return (
            <div>
                <table>
                    <tr>
                        <th>version</th>
                        {headline}
                    </tr>
                    {content}
                </table>
                <div>
                    generated from poc-dependencies, version: 0.0.1, Copyright Michael Krauße 2020
                </div>
            </div>

        );
    }

}

export default Overview;