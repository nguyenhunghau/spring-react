import React, { useState } from "react";
import { Tab, Tabs } from "react-bootstrap";

const HomeTab = () => {
    return (
        <Tabs defaultActiveKey="profile" id="uncontrolled-tab-example">
            <Tab eventKey="Area" title="Area">
                <div class="chart tab-pane active" id="revenue-chart">
                    <canvas id="revenue-chart-canvas" height="300" ></canvas>
                </div>
                {/* <Sonnet /> */}
            </Tab>
            <Tab eventKey="profile" title="Profile">
                <div class="chart tab-pane" id="sales-chart">
                    <canvas id="sales-chart-canvas" height="300"></canvas>
                </div>
                {/* <Sonnet /> */}
            </Tab>
        </Tabs>

    )
}
export default HomeTab;