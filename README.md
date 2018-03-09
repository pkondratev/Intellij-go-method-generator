Goland Method Generator
=======================

Plugin for Intellij Idea and Goland (can be installed in any Jetbrains product). Use it to add new syntaxis for Golang methods.

What does it do?
----------------

For example, you create a new struct in your Go project:
```go
type MyStruct struct {

}
```
And now you need to create a lot of methods for it:
```go
func (m *MyStruct) MyMethod(s string) bool {

}
```
You do not need to write a lot of same code, just write
```go
MyStruct.MyMethod(s string) bool
```
and press `CTRL + ALT + SHIFT + G` to replace upper line with:
```go
func (this *MyStruct) MyMethod(s string) bool {

}
```
Very simple.

How to install?
---------------

The easiest way to install package from `Plugins` prefference in your IDEA.  
Second way is install it from *.jar. Navigate to `release` tab on github and download jar or build plugin with JDK 8 and IDEA SDK from source. Now you need to open your favorite Jetbrains IDEA, open `setting`, navigate to `plugins` menu, select `install from disk` and restart yout IDE.

Some important notes:
---------------------
* First of all, this plugin works only in files with extension `*.go`, for example, "main.go".  
* Secondly, if named struct doesn't exist in this file, plugin will not generate any realisations (you can change this behavior in source code by removing check for existing struct, maybe in future will be added to preferences).  
* Cursor must be on same line with your prototype.

What can I write?
-----------------
```go
// From
MyStruct.MyMethod // CTRL + ALT + SHIFT + G
// To
func (this *MyStruct) MyMethod() {

}

// Same from
MyStruct.MyMethod() // CTRL + ALT + SHIFT + G
// To
func (this *MyStruct) MyMethod() {

}

// from
MyStruct.MyMethod(s string) // CTRL + ALT + SHIFT + G
// To
func (this *MyStruct) MyMethod(s string) {

}

// from
MyStruct.MyMethod(s string) bool // CTRL + ALT + SHIFT + G
// To
func (this *MyStruct) MyMethod(s string) bool {

}

// from
MyStruct.MyMethod(s string) (int, error) // CTRL + ALT + SHIFT + G
// To
func (this *MyStruct) MyMethod(s string) (int, error) {

}
```