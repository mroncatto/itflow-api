ALTER TABLE "account"
ALTER "avatar" DROP DEFAULT,
ALTER "active" SET DEFAULT true,
ALTER "non_locked" SET DEFAULT true,
ALTER "avatar" DROP NOT NULL;