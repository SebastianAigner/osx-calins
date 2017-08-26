# OS X Calendar Inspector [![Build Status](https://travis-ci.org/SebastianAigner/osx-calins.svg?branch=master)](https://travis-ci.org/SebastianAigner/osx-calins) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/b6de763c4d3544938c5d7e6713b79b6d)](https://www.codacy.com/app/sebastian-aigner/osx-calins?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=SebastianAigner/osx-calins&amp;utm_campaign=Badge_Grade)


Inspect and export subscribed OS X calendars, get subscription URLs for iCloud calendars.
![Screenshot of osx-calins](https://cloud.githubusercontent.com/assets/2178959/15265237/b165e596-197f-11e6-96e4-442057a4c4bd.png)
#Description
The OSX Calendar Inspector is a little tool that makes it possible to create a report of all the currently configured calendars in your "Calendar" app on Mac OS X, thus helping you exporting them and subscribing to them on all the platforms of your liking!

# Usage
This program is super simple. Open the GUI and you'll be presented with a table of the names of your calendars and the appropriate CalDAV URLs. Double-click on an entry to copy its CalDAV URL into your clipboard.

Use the buttons on the bottom of the user interface to export to plain text and HTML.

# Why?
If you are on your way through life on more platforms that just OS X, it might be desirable to be able to subscribe to your iCloud calendars on your other devices (Android, platforms where you're running Thunderbird etc.).

For some reason, Apple makes it very hard to subscribe to your iCloud calendars with other devices. This program solves that issue.

# How?
This project scans the `~/Library/Calendars`directory of the current user, parses through the endless barrage of weirdly named subfolders and contained `plist` files and displays the relevant information in a clear fashion.
