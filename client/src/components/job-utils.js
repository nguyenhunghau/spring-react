import React from "react";

export const makeSalaryMin = (description) => {
    let myObject = JSON.parse(description);
    if ('salaryMin' in myObject) {
        return parseInt(myObject.salaryMin);
    }
    let salary = myObject.salary.replace(/\$|,|\.|\+USD|usd|m vnd/g, '').trim();
    var salaryArray = salary.split(' ');
    if (salary.startsWith('From') && !isNaN(salaryArray[salaryArray.length - 1])) {
        return parseInt(salaryArray[salaryArray.length - 1]);
    } else if (!isNaN(salaryArray[0])) {
        return parseInt(salaryArray[0]);
    }
    return 0;
}

export const makeSalaryMax = (description) => {
    let myObject = JSON.parse(description);
    if ('salaryMax' in myObject) {
        return Math.max(parseInt(myObject.salaryMax), parseInt(myObject.jobSalary));
    }
    let salary = myObject.salary.replace(/\$|,|\.|\+|USD|usd|m vnd/g, '').trim();
    var salaryArray = salary.split(' ');
    if (salary.startsWith('From')) {
        return -1;
    }
    if (salary.startsWith('Up') || !isNaN(salaryArray[salaryArray.length - 1])) {
        return parseInt(salaryArray[salaryArray.length - 1]);
    }
    return -1;
}