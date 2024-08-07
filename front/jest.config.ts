module.exports = {
    preset: 'jest-preset-angular',
    setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
    testPathIgnorePatterns: [
        "/node_modules/",
        "/dist/",
    ],
    globals: {
        'ts-jest': {
        tsconfig: 'tsconfig.spec.json',
        stringifyContentPathRegex: '\\.html$',
        },
    },
    moduleNameMapper: {
        '^@/(.*)$': '<rootDir>/src/app/$1',
    },
};