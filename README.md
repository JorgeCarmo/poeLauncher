# Path of Exile Launcher

## Introduction

> Path of Exile is a great game that is often complemented by 3rd party tools to aid the player during their journey. Having played the game for several years and having run those tools thousands of times I set to create a tool to aggregate everything and launch them with ease. This created poeLauncher. My aim is to provide a simple but powerful tool that let's the player add their own tools to it while being able to share their configs with everyone else.

## Code Samples

> Example of adding a github tool:

> [PoeTradeMacro]
active = false  <------------ start the tool/not
version = "v2.8.0" <------------ latest github version (only used if selfupdates is set to false)
type = github <-------------- download location (currently only github implemented)
link = PoE-TradeMacro/POE-TradeMacro/releases/latest <-------------- link to download
runfilename = Run_TradeMacro.ahk <--------------- executable file
selfupdates = true <---------------- if set to true the launcher does not update this tool
toolDescription = Trade Macro <---------------- small description of the tool



## Installation

> 1. Got to the releases page and download the latest release
> 
> 2. Copy/create .ini file of the tools to the ini folder
> 3. Execute 
