# rider-plugins

This plugin contains some utils that the people of Mad Llama developed
for Jetbrains Rider to further aid the Unity game programmers.

## Installation

To install this plugin first download the latest release. Then, in your IDE install the plugin from 
`Perferences -> Plugins -> settings -> Install Plugin from Disk`.

## Generate Singleton
This action adds Singleton to selected `Monobehaviour` class. It uses `Awake` method of `Monobehaviour`.
To use the singleton instance, just call `YourMonobehaviour.Instance` from any part of the project.

