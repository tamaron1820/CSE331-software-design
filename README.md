# Campus Map Application

Campus のマップに出発地と到着地の最短経路を求める

# DEMO
![sample2](https://user-images.githubusercontent.com/104906428/227012433-629ed6b5-7125-4544-bdca-51ed7f443103.gif)
<img width="199" alt="sample1" src="https://user-images.githubusercontent.com/104906428/227012100-fcc9bd03-311b-4db6-9589-f53bb759508e.png">
<img width="196" alt="sample3" src="https://user-images.githubusercontent.com/104906428/227012310-598d81f8-ee0f-419e-bf0b-86f2b326a920.png">

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
