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

        this.loadAndSetState(driver);
    }

    loadAndSetState(driver) {
        var session = driver.session();

        var artifacts = [];
        let keys = new Set();

        session.run("match (n)-[:DEPENDS_ON]->(k) return n,k;").then((result) => {
            session.close();
            for (var recordId in result.records) {
                const projectName = result.records[recordId].get(0).properties.artifact_name;
                const projectVersion = result.records[recordId].get(0).properties.artifact_version;
                const dependencyName = result.records[recordId].get(1).properties.artifact_name;
                const dependencyVersion = result.records[recordId].get(1).properties.artifact_version;
                const artifact = this.getOrCreateArtifact(artifacts, projectName, projectVersion);
                artifact.directDependencies[dependencyName] = dependencyVersion;

                keys.add(dependencyName);
            }

            this.setState({
                keys: Array.from(keys),
                content: artifacts
            });
        });
    }

    artifactAdded(artifacts, projectName) {
        if (artifacts.length === 0) {
            return false;
        }
        for (const artifact of artifacts) {
            if (artifact.projectName === projectName) {
                return true;
            }
        }
        return false;
    }

    getOrCreateArtifact(artifacts, projectName, projectVersion) {
        if (!this.artifactAdded(artifacts, projectName)) {
            const artifact = {
                "projectName": projectName,
                "version": projectVersion,
                "directDependencies": {}
            };
            artifacts.push(artifact);
        }
        for (const artifact of artifacts) {
            if (artifact.projectName === projectName) {
                return artifact;
            }
        }
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
