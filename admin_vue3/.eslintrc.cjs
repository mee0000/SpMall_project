module.exports = {
    "env": {
        "browser": true,
        "es2021": true
    },
    "extends": [
        "eslint:recommended",
        "plugin:@typescript-eslint/recommended",
        "plugin:vue/vue3-essential"
    ],
    "overrides": [
        {
            "env": {
                "node": true
            },
            "files": [
                ".eslintrc.{js,cjs}"
            ],
            "parserOptions": {
                "sourceType": "script"
            }
        }
    ],
    "parserOptions": {
        "ecmaVersion": "latest",
        "parser": "@typescript-eslint/parser",
        "sourceType": "module"
    },
    "plugins": [
        "@typescript-eslint",
        "vue"
    ],
    "rules": {
        "no-var": "error", // let 혹은 const 사용 var 사용하지 않음
        "no-multiple-empty-lines": ["warn", { max : 1}],
        "no-console": process.env.NODE_ENV === "production" ? "error" : "off",
        "no-debugger": process.env.NODE_ENV === "production" ? "error" : "off",
        "no-unexpected-multiline": "error",
        "no-useless-escape": "off",
        //typeScript(https://typescript-eslint.io/rules)
        "@typescript-eslint/no-unused-vars": "error",
        "@typescript-eslint/prefer-ts-expect-error": "error",
        "@typescript-eslint/no-explicit-any": "off",
        "@typescript-eslint/no-non-null-assertion": "off",
        "@typescript-eslint/no-namespace": "off",
        "@typescript-eslint/semi": "off",

        // eslint-plugin-vue
        "vue/multi-word-component-names": "off",
        "vue/script-setup-uses-vars": "error",
        "vue/no-mutating-props": "off",
        "vue/attribute-hyphenation": "off",

      
        "auto-rename-tag.activationOnLanguage": ["html", "xml", "php", "javascript"],
        "auto-close-tag.SublimeText3Mode": true
         
    }
}
