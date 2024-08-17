test_backend:
	./gradlew test

lint_backend:
	./gradlew spotlessKotlinApply

pre-commit: lint_backend test_backend
