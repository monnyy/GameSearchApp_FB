// const axios = require('axios')
// const url = 'http://checkip.amazonaws.com/';

'use strict';
console.log('Loading hello world function');

const json_data = require('./games.json');

/**
 *
 * Event doc: https://docs.aws.amazon.com/apigateway/latest/developerguide/set-up-lambda-proxy-integrations.html#api-gateway-simple-proxy-for-lambda-input-format
 * @param {Object} event - API Gateway Lambda Proxy Input Format
 *
 * Context doc: https://docs.aws.amazon.com/lambda/latest/dg/nodejs-prog-model-context.html
 * @param {Object} context
 *
 * Return doc: https://docs.aws.amazon.com/apigateway/latest/developerguide/set-up-lambda-proxy-integrations.html
 * @returns {Object} object - API Gateway Lambda Proxy Output Format
 *
 */
exports.lambdaHandler = async (event, context) => {

    const raw = JSON.parse(JSON.stringify(json_data, null, "\t"));

    let count = 0;

    let results = [];

    if (event.queryStringParameters && event.queryStringParameters.requiredResults) {
        console.log("Received count: " + event.queryStringParameters.requiredResults);
        count = Number(event.queryStringParameters.requiredResults);
    }

    if (count > 0) {
        results = raw.slice(0, count);
    }
    // let results = raw.map(function(entry) {
    //     var match = entry.title.toLowerCase().indexOf("smoke") !== -1;
    //     return match ? entry : null;
    // }).filter(function (el) {
    //     return el != null;
    // });
    let response = {
        statusCode: 200,
        headers: {
            "x-custom-header" : "my game list"
        },
        body: JSON.stringify(results)
    };
    return response;
    // return results;
};