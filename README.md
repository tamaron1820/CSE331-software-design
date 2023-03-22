# Campus Map Application

Campus のマップに出発地と到着地の最短経路を求める

# DEMO
![sample2](https://user-images.githubusercontent.com/104906428/227009995-a678c207-d537-4183-a283-a26a2171d901.gif)

# Features

最短経路にはDijkstra's algorithmを用いる
フロントエンドにはReact
バックエンドはJava
建物とそのつながりをNodeとEdgeとしてGraphで管理する
Startの建物を選択、Destinationの建物を選択、Drawで最短経路を描画

# Installation

```bash
npm install --no-audit
```

# Usage
サーバー立ち上げ
```bash
git clone git@github.com:tamaron1820/CSE331-software-design.git
cd CSE331-software-design
./gradlew hw-campuspaths-server:runSpark
```

フロント立ち上げ
```bash
git clone git@github.com:tamaron1820/CSE331-software-design.git
cd CSE331-software-design/hw-campuspaths
npm start
```

# License
Campus Map Application is under [MIT license]
