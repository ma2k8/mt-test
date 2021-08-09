# ファイル変更監視付きでローカルサーバーを立ち上げる
# 事前にdocker-compose up & make migrateの実行が必要
run:
	./gradlew -t installDist &
	./gradlew run

# dbマイグレーション
migrate:
	./gradlew flywayMigrate

# dbのデータをすべて削除し、マイグレーション適用前の状態に戻す
clean:
	./gradlew flywayClean

# dbMigrationエラー状態を復旧する
repair:
	./gradlew flywayRepair
