# Contributing

## Commit messages

Commits follow [Conventional Commits 1.0.0](https://www.conventionalcommits.org/en/v1.0.0/):

```
<type>(<scope>): <description>
```

- `type` is one of `feat`, `fix`, `refactor`, `perf`, `docs`, `test`, `style`, `build`, `ci`, `chore` or `revert`.
- `scope` is optional. Use the module or area the change touches, or `deps` for dependency bumps.
- Write the description in the imperative, starting lowercase, with no full stop at the end.
- Mark a breaking change with `!` before the colon.
- Keep the subject under about 72 characters. Only add a body to reference an issue, such as `Fixes #12`.
- Put one change in each commit. Unrelated fixes go in separate commits.

Examples:

```
fix: keep the menu open after resizing the window
chore(deps): bump Gradle to 9.2
feat!: rename the config file
```
