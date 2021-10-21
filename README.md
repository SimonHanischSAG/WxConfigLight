# WxConfigLight
Lightweight and free webMethods IntegrationServer package with a simialar but very reduced API like the official and the one and only WxConfig.
It is designed for a simple introduction into configuration management. For advanced usage
please switch to the official WxConfig.

<h2>How to use</h2>

<h3>Global variables</h3>

<b>The most important thing to say is: WxConfigLight is using the "Global Variables" from IntegrationServer. That is the place where updates are executed and where keys are red from!</b>

In order to use a wxconfig.cnf file like with the official WxConfig like this:
******
myKey=myValue

myList=1

myList=2
******

you have to run

http://localhost:5555/invoke/wx.config.admin:replaceVariablesWithGlobalFile?wxConfigPkgName=MyPackage

It will create Global Variables like this:

WxConfigLight__MyPackage__myKey: myValue
WxConfigLight__MyPackage__myList__: 1
WxConfigLight__MyPackage__myList__1: 2

After loading that one time you can now work on these Global Variables like you are used to. 

<b>Consider that two underlines ("__") are a reserved string. Do not use that in your package names and keys!</b>

Hint: You can drop that stuff if you want with

http://localhost:5555/invoke/wx.config.admin:clearAllVariablesOfPackage?wxConfigPkgName=MyPackage

<h3>Public services</h3>

<ul>
  <li>wx.config.pub:addValue</li>
<li>wx.config.pub:getValue</li>
  </ul>
wx.config.pub:getValueList
wx.config.pub:removeValue
wx.config.pub:updateValue
