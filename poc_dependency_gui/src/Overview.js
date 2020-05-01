import React from "react";

import content from "./data/data.json"

/**
 * Displays a simple overview for all artifacts.
 */
class Overview extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            keys: this.calculos(content.content),
            "content": content.content
        };
    }

    renderConsumer(element, displayKeys) {
        return displayKeys.map((keyValue) => <td>{element.directDependencies[keyValue]}</td>);
    }

    calculos(liste) {
        let result = new Set()
        liste.map((element) => {
            for(const property in element.directDependencies) {
                result.add(property);
            }
        });


        return Array.from(result.values());
    }

    render() {
        const headline = this.state.keys.map((element) => <th>{element}</th>);
        const content = this.state.content.map((element) => <tr>
            <td id={'projectname.' + element.projectName} key={'projectname.' + element.projectName}>{element.projectName}</td>
            <td>{element.version}</td>
            {this.renderConsumer(element, this.state.keys)}</tr>)
        return (
            <div>
                <table>
                    <thead>
                    <tr>
                        <th>consumer</th>
                        <th>version</th>
                        {headline}
                    </tr>
                    </thead>
                    <tbody>
                    {content}
                    </tbody>
                </table>

            </div>

        );
    }

}

export default Overview;