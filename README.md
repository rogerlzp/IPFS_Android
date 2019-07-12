# IPFS 节点分享

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

IPFS, 整体架构学习GeekNews，结合IPFS和钱包的功能，给用户通过去中心化的节点来分享的功能。

key steps:
provider part
1. copy ipfs binary to a directory in android app
2. start ipfs daemon, with option --enable-pubsub-experiment
3. add a file (take a picture for example), the hash of the file, set the description of the file, and the number of token should be payed to get the file, save this bean in the sqlite database . you may put more files.
4. add the json file for these files generated from sqlite database, get the hash of this json file
5. name publish the hash of json file, so that others can find it by the hash of the node
6. pubsub the node with the topic “PUBLISH_NODE”, the data is "NAMEHASH:#nodehash;TOTAL_PRICE:100;PUBLISH_WALLET_ADDRESS:#walletAddress; others who subscribe the topic will get the hash of the node

consumer part:
1. sub the topic “PUBLISH_NODE”, get the hash of the node, the total price of the node, and the wallet address, save them into local database
2. choose one node, load the contract of the TOKEN used, get the PUBLISH_WALLET_ADDRESS, your wallet address, and how much token, click to pay it, which will run the smart contract to transfer the token from your wallet address to the PUBLISH_WALLET_ADDRESS.
3. after pay for it, get the json file
4. show the images listed in the json file.

People can sell their digital file with this app, no center server is used, and no one can stop it.
Also there are several issues should be resolved carefully, especially in the encryption step to make sure the digital product can be decrypted correctly only after payed.

## Version


1.提交第一版

## Thanks

### APP:
[Hot](https://github.com/zj-wukewei/Hot) 提供了Dagger2配合MVP的架构思路



### RES:
[iconfont](http://www.iconfont.cn/) 提供了icon素材

[material UP](http://www.material.uplabs.com/) 提供了Material Design风格的素材

### LIB:
#### UI
* [MaterialCalendarView](https://github.com/prolificinteractive/material-calendarview)
* [MaterialSearchView](https://github.com/MiguelCatalan/MaterialSearchView)
* [PhotoView](https://github.com/chrisbanes/PhotoView)
* [multiline-collapsingtoolbar](https://github.com/opacapp/multiline-collapsingtoolbar)
* [glide-transformations](https://github.com/wasabeef/glide-transformations)
* [html-textview](https://github.com/SufficientlySecure/html-textview)

#### RX

* [RxJava](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [RxPermissions](https://github.com/tbruyelle/RxPermissions)

#### NETWORK

* [Retrofit](https://github.com/square/retrofit)
* [OkHttp](https://github.com/square/okhttp)
* [Glide](https://github.com/bumptech/glide)
* [Gson](https://github.com/google/gson)
* [Jsoup](https://github.com/jhy/jsoup)

#### DI

* [Dagger2](https://github.com/google/dagger)
* [ButterKnife](https://github.com/JakeWharton/butterknife)

#### FRAGMENT

* [Fragmentation](https://github.com/YoKeyword/Fragmentation)

#### LOG

* [Logger](https://github.com/orhanobut/logger)

#### DB

* [Realm](https://github.com/realm/realm-java)

#### CANARY

* [BlockCanary](https://github.com/markzhai/AndroidPerformanceMonitor)
* [LeakCanary](https://github.com/square/leakcanary)

## License

Copyright (c) 2018 IPFS.应用

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

