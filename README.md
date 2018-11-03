##Drawing-Board

基于openCV识别开发的画板程序

解压Drawingboard.zip，将解压的文件导入到Intellij IDEA中，add frameworks support Maven。

#### 安装openCV

* 在Windows下安装

首先，您应该从[这里](http://opencv.org/releases.html)下载OpenCV库（版本3.x）。

然后，在您选择的位置提取下载的OpenCV文件。一旦你把文件夹`opencv`放在你喜欢的任何地方。

现在，您需要的只有两件事：`opencv-3xx.jar`位于的文件`\opencv\build\java`和`opencv_java3xx.dll`位于`\opencv\build\java\x64`（对于64位系统）或`\opencv\build\java\x86`（对于32位系统）的库。每个文件的3xx后缀是当前OpenCV版本的快捷方式，例如，OpenCV 3.0 为300，OpenCV 3.3为330。

* 在MacOS安装OpenCV 3.X

在macOS下获取OpenCV的最快方法是使用[Homebrew](http://brew.sh/)。安装Homebrew后，您必须检查系统上是否已安装XCode命令行工具。

为此，请打开终端并执行： 如果macOS要求安装此类工具，请继续下载和安装。否则，继续安装OpenCV。`xcode-select --install`

作为先决条件，请检查是否已安装Apache Ant。否则，使用Homebrew安装： 。Ant应该可以在。`brew install ant``/usr/local/bin/ant`

要通过自制安装OpenCV（带有Java支持），您需要编辑*的OpenCV*在自制配方，增加对Java的支持： 在文本编辑器打开，更改行： 在 然后，保存文件后，可以有效安装OpenCV：`brew edit opencv``-DBUILD_opencv_java=OFF``-DBUILD_opencv_java=ON``brew install --build-from-source opencv`

在安装OpenCV之后，所需的jar文件和dylib库将位于`/usr/local/Cellar/opencv/3.x.x/share/OpenCV/java/`例如`/usr/local/Cellar/opencv/3.3.1/share/OpenCV/java/`。

请注意，如果您从以前的版本更新OpenCV，则此方法不起作用：您需要卸载OpenCV并再次安装。

参考：<https://opencv-java-tutorials.readthedocs.io/en/latest/01-installing-opencv-for-java.html#install-opencv-3-x-under-macos>

#### 导入opencv3.x.x.jar

1.IDEA中点击File->Project Structure->Libraries-> + ->将下载的opencvJAR包加入

2.Run->Edit Configurations->使用VM参数指定库的位置`-Djava.library.path=/opencv/build/lib`

3.运行Main.java，按钮栏从左到右分别的功能为：绘画，改变笔画颜色，识别，保存，打开，撤销，清空。



