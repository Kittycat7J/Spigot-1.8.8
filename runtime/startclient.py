# -*- coding: utf-8 -*-
"""
Created on Fri Apr  8 16:54:36 2011

@author: ProfMobius
@version: v1.0
"""

import sys
import logging
import json
from optparse import OptionParser

from commands import Commands, CLIENT


def main():
    parser = OptionParser(version='MCP %s' % Commands.fullversion())
    parser.add_option('-c', '--config', dest='config', help='additional configuration file')
    parser.add_option('-m', '--main', dest='mainclass', help='Main class to start', default='Start')
    parser.add_option('-j', '--json', dest='json', action='store_true', help='Use the json file to setup parameters', default=False)
    parser.add_option('-d', '--debug', dest='debug', action='store_true', help='Start the client in debug mode for IntelliJ (port 5005)', default=False)
    options, _ = parser.parse_args()
    startclient(options.config, options.mainclass, options.json, options.debug)
    logging.info(options)


def startclient(conffile, mainclass, jsonoverride, debug):
    try:
        commands = Commands(conffile)

        # Only add debugger arguments if debug is enabled
        debugger_args = "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
        extraargs = debugger_args if debug else ""

        if jsonoverride:
            jsonData = json.load(open(commands.jsonFile))
            mainclass = jsonData['mainClass']
            extraargs += " " + jsonData['minecraftArguments']  # Append Minecraft arguments if any

        if not commands.checkbins(CLIENT):
            commands.logger.warning('!! Can not find client bins !!')
            sys.exit(1)
        
        # Start the client with the main class and extra arguments
        commands.startclient(mainclass, extraargs)
        logging.info("Main class: %s, Extra arguments: %s", mainclass, extraargs)
    except Exception:
        logging.exception('FATAL ERROR')
        sys.exit(1)


if __name__ == '__main__':
    main()
