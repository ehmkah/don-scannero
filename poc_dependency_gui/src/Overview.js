import React from "react";

/**
 * Displays a simple overview for all artifacts.
 */
class Overview extends React.Component {

    constructor(props) {
        super(props);
        this.state = {keys: ["", "gradleVersion", "org.junit.jupiter:junit-jupiter", "com.nhaarman.mockito", "kotlin2:mockito-kotlin"]};
    }

    render() {
        const headline = this.state.keys.map((element) => <th>{element}</th>);

        return (
            <div>
                <table>
                    <tr>
                        {headline}
                    </tr>

                </table>
                <h1>hallo</h1>
            </div>
        );
    }

};

export default Overview;