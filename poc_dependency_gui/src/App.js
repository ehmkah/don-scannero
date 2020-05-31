import React from 'react';
import './App.css';
import Overview from "./Overview";
import Copyright from "./Copyright";
import neo4j from 'neo4j-driver'

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            keys: [],
            content: {}
        }
    }

    componentDidMount() {
        const driver = neo4j.driver("bolt://localhost:7687", neo4j.auth.basic("", ""))
        driver.onCompleted = () => {
            console.log('Driver created')
        }

        driver.onError = error => {
            console.log(error)
        }

        const session = driver.session()
        session.run('match(n)-[r:DEPENDS_ON]->(k) where n.artifact_name = \'unknown\' return k\n').then((result) => {
            session.close()
            var content = [];
            var directDependencies = {};
            for (var dependency in  result.records) {
                directDependencies[result.records[dependency].get(0).properties.artifact_name] = result.records[dependency].get(0).properties.artifact_version;
            }

            var entry = {
                "projectName": "unknown",
                "directDependencies": directDependencies
            }

            content.push(entry);

            this.setState({
                keys: this.calculos(content),
                content: content
            });
            //console.log(content);
            // ... on application exit:
            driver.close()
        })

        /**  var url = 'http://localhost:3000/data/data.json';
         if (process.env.NODE_ENV === 'development') {
            url = 'http://localhost:3001/data/data.json'
        }

         fetch(url)
         .then(res => res.json())
         .then(
         (result) => {
                    this.setState({
                        keys: this.calculos(result.content),
                        content: result.content
                    });
                },
         // Note: it's important to handle errors here
         // instead of a catch() block so that we don't swallow
         // exceptions from actual bugs in components.
         (error) => {
                    alert(error);
                    this.setState({
                        isLoaded: true,
                        error
                    });
                }
         )

         **/
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
