import React from 'react';
import { render } from '@testing-library/react';
import Overview from "./Overview";

const testContent = {
  "description": "Overview for some testdata",
  "content": [
    {
      "projectName": "imglib",
      "version": "1.4.0",
      "directDependencies": {
        "gradleVersion": "6.3",
        "org.junit.jupiter:junit-jupiter": "5.6.2",
        "com.nhaarman.mockitokotlin2:mockito-kotlin": "2.2.0"
      }
    },

    {
      "projectName": "fooProject",
      "version": "1.1.0",
      "directDependencies": {
        "gradleVersion": "4.10.3",
        "org.junit.jupiter:junit-jupiter": "5.0"
      }
    }
  ]
}


test('renders overview', () => {
  const { getByText } = render(<Overview />);
  const datarendered = getByText(/imglib/);
  expect(datarendered).toBeInTheDocument();
});

test('generateKeys', () => {
  const overview = new Overview();
  const actual = overview.calculos(testContent.content);

  expect(actual.length).toBe(3);
  expect(actual).toContain("org.junit.jupiter:junit-jupiter")
  expect(actual).toContain("gradleVersion")
  expect(actual).toContain("com.nhaarman.mockitokotlin2:mockito-kotlin")
})


