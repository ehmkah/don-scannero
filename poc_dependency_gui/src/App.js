import React from 'react';
import './App.css';
import Overview from "./Overview";
import Copyright from "./Copyright";
import content from "./data/data.json";

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            keys: [],
            content: {}
        }
    }

    componentDidMount() {
        this.setState({
            keys: this.calculos(content.content),
            content: content.content
        });
    }

    calculos(liste) {
        let result = new Set()
        liste.map((element) => {
            for (const property in element.directDependencies) {
                result.add(property);
            }
        });

        return Array.from(result.values());
    }

    render() {
        return (
            <div className="App">
                <div>Hello - show some consumers and there dependencies. Hope you like it</div>
                <Overview keys={this.state.keys} content={this.state.content}/>
                <Copyright/>
            </div>
        );
    }
}

export default App;
