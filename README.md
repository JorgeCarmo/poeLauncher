
# Path of Exile Launcher

## Introduction

Path of Exile is a great game that is often complemented by 3rd party tools to aid the player during their journey. Having played the game for several years and having run those tools thousands of times I set to create a tool to aggregate everything and launch them with ease. This created poeLauncher. My aim is to provide a simple but powerful tool that let's the player add their own tools to it while being able to share their configs with everyone else.

## Code Samples

Example of adding a github tool:

	    [PoeTradeMacro]
        active = false
        version = "v2.8.0"
        type = github
        link = PoE-TradeMacro/POE-TradeMacro/releases/latest
        runfilename = Run_TradeMacro.ahk
        selfupdates = true
        toolDescription = Trade Macro

 - active - indicates if the tool should be started
 - version - latest github version (only used if selfupdates is set to false)
 - type - tool location/type (currently only github implemented)
 - link - link to download
 - runfilename - executable file to be run
 - selfupdates - if set to true the launcher does not update this tool
 - toolDescription - small description of the tool

## Installation

 1. Got to the releases page and download the latest release

 2. Copy/create .ini file of the tools to the ini folder

 4. Execute poeLauncher.exe
 
