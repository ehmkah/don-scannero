import React from 'react';
import {render} from '@testing-library/react';
import Overview from "./Overview";

const testContent = [
    {
        "projectName": "imglib",
        "version": "1.4.0",
        "directDependencies": {
            "gradleVersion": "6.3",
            "org.junit.jupiter:junit-jupiter": "5.6.2",
            "com.nhaarman.mockitokotlin2:mockito-kotlin": "2.2.0"
        }
    }];

test('renders overview', () => {

    const {getByText} = render(<Overview keys={["a"]} content={testContent}/>);
    const datarendered = getByText(/imglib/);
    expect(datarendered).toBeInTheDocument();
});




