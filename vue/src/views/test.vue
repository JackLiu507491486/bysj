<template>
  <div>
    <div v-html="formattedMessage"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      message: `This is some text outside of code block.
      This is a code block:
      \`\`\`
      const message = "Hello, world!";
      console.log(message);
      \`\`\`
      This is some text outside of code block again.`,
    };
  },
  computed: {
    formattedMessage() {
      let formatted = this.message;
      const codeBlockRegex = /```([\s\S]*?)```/g;
      let match;
      while ((match = codeBlockRegex.exec(this.message)) !== null) {
        const codeBlock = match[1];
        const wrappedCodeBlock = `<div class="code-block">${codeBlock}</div>`;
        formatted = formatted.replace(match[0], wrappedCodeBlock);
      }
      return formatted;
    }
  }
};
</script>

<style>
.code-block {
  background-color: black; /* 背景色设置为黑色 */
  color: white; /* 文字颜色设置为白色 */
  padding: 10px;
  margin-bottom: 10px;
}
</style>
