import React from "react";


/**
 * Displays a simple overview for all artifacts.
 */
class Overview extends React.Component {

    renderConsumer(element, displayKeys) {
        return displayKeys.map((keyValue) => <td>{element.directDependencies[keyValue]}</td>);
    }


    render() {
        if (this.props.keys.length === 0) {
            return (<div/>);
        }

        const headline = this.props.keys.map((element) => <th>{element}</th>);
        const content = this.props.content.map((element) => <tr>
            <td id={'projectname.' + element.projectName}
                key={'projectname.' + element.projectName}>{element.projectName}</td>
            <td>{element.version}</td>
            {this.renderConsumer(element, this.props.keys)}</tr>)
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