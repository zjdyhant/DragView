# DragView
Using in unread messages in ListView. You can drag the unread message  like QQ.
## GIF
![](https://github.com/zjdyhant/DragView/blob/master/app/src/main/res/raw/dragview.gif)
## Layout
```Java
<?xml version="1.0" encoding="utf-8"?>
<com.mm.hant.dragview.widget.DragView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drag_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ListView
        android:id="@+id/list_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />


</com.mm.hant.dragview.widget.DragView>
```
###思路：
        1，点击ListView item里的未读消息(TextView1)的时候，在DragView里添加跟未读消息一样的TextView2,同时隐藏TextView1。
        2，移动的时候，绘制红色圆点和路径。
        3，手指抬起的时候，根据离TextView1点的距离判断是否将TextView1移除。
        Tips：TextView1需要重写，捕获action_down事件。如果用setOnClickListener来做，会消费掉点几事件，导致DragView无法捕获。
###DragView的责任：
        1，当ListView的未读消息TextView被电击的时候，new一个未读消息TextView并add到界面上。
        2，绘制小红点和连接两个圆的中间部分。中间部分通过二阶贝塞儿曲线绘制。
        3，监听KeyEvent事件，当ACTION_UP的时候回调。
###使用：
        参考demo代码。代码中都有写注释。
