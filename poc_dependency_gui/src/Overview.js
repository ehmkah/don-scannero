import React from "react";


/**
 * Displays a simple overview for all artifacts.
 */
class Overview extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            keys: props.keys,
            content: props.content
        };
    }

    renderConsumer(element, displayKeys) {
        return displayKeys.map((keyValue) => <td>{element.directDependencies[keyValue]}</td>);
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