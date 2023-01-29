# TamagoRPG

たまご鯖RPGの基幹システムを提供するプラグイン

## TODO

実装すべき機能

- [x] プレイヤーデータのシステム
- [x] ダンジョンのシステム
- [x] カスタムアイテムの機能
- [ ] クエストのシステム
- [ ] スキルのシステム
- [ ] ストーリーを流すシステム

## コマンド

大文字の単語はプレースホルダーを、`[]`で囲まれた単語はオプションを表しています。

### アクションコマンド

コマンドブロックやポータルに設定することで特定のアクションを実行します。

- `/rpg movetodungeon DUNGEON` - コマンド実行者のダンジョンへの移動を試みる
- `/rpg dungeoncompleted DUNGEON [DELAY]` - ダンジョンをクリア状態にし、指定時間後にダンジョン内のプレイヤーをオーバーワールドへテレポートさせ、ダンジョンのリセットを行う
- `/rpg dungeonover DUNGEON [DELAY]` - ダンジョンをゲームオーバー状態にし、指定時間後にダンジョン内のプレイヤーをオーバーワールドへテレポートさせ、ダンジョンのリセットを行う
- `/rpg createprofile` - コマンド実行者のプロフィールを作成する

### 管理用コマンド

プラグインが保持しているデータを編集することができます。

#### ダンジョン

- `/rpgm dungeon create DUNGEON` - ダンジョンを作成する
- `/rpgm dungeon delete DUNGEON` - ダンジョンを削除する
- `/rpgm dungeon list` - ダンジョンの一覧を表示する
- `/rpgm dungeon info DUNGEON` - ダンジョンの情報を表示する
- `/rpgm dungeon setdisplayname DUNGEON DISPLAY_NAME` - ダンジョンの表示名を設定する
- `/rpgm dungeon addrequired DUNGEON REQUIRED_DUNGEON` - ダンジョンの必須ダンジョンを追加する
- `/rpgm dungeon removerequired DUNGEON REQUIRED_DUNGEON` - ダンジョンの必須ダンジョンを削除-する
- `/rpgm dungeon setmax DUNGEON MAX` - ダンジョンの最大人数を設定する
-  `/rpgm dungeon setlevel DUNGEON LEVEL` - ダンジョンの必須レベルを設定する
- `/rpgm dungeon lock DUNGEON` - ダンジョンをロックする
- `/rpgm dungeon unlock DUNGEON` - ダンジョンのロックを解除する 

#### プレイヤー

- `/rpgm player setrole PLAYER ROLE` - プレイヤーの役職を設定する
- `/rpgm player adddungeon PLAYER DUNGEON` - プレイヤーがクリアしたダンジョンを追加する
- `/rpgm player removedungeon PLAYER DUNGEON` - プレイヤーがクリアしたダンジョンを削除する
- `/rpgm player setlevel PLAYER LEVEL` - プレイヤーのレベルを設定する
- `/rpgm player info PLAYER` - プレイヤーの情報を表示する

### カスタムアイテム

- `/custom-give PLAYER CUSTOM_ITEM [AMOUNT]` - カスタムアイテムをプレイヤーに付与する
- `/custom-json CUSTOM_ITEM` - カスタムアイテムのJSONデータを表示する
