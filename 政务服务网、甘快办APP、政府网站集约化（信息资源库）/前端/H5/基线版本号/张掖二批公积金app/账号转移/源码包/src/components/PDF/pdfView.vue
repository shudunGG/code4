<template>
  <div class="pdf-viewer-container">
    <canvas ref="canvas"></canvas>
  </div>
</template>

<script>
import pdfjsLib from 'pdfjs-dist'

export default {
  name: 'PDFViewer',
  props: {
    src: {
      type: String,
      required: true
    }
  },
  mounted() {
    this.renderPDF()
  },
  methods: {
    async renderPDF() {
      const canvas = this.$refs.canvas
      const loadingTask = pdfjsLib.getDocument(this.src)
      const pdf = await loadingTask.promise
      const page = await pdf.getPage(1)
      const viewport = page.getViewport({ scale: 1 })
      const context = canvas.getContext('2d')
      canvas.height = viewport.height
      canvas.width = viewport.width

      const renderContext = {
        canvasContext: context,
        viewport: viewport
      }
      await page.render(renderContext)
    }
  }
}
</script>

<style scoped>
.pdf-viewer-container {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

canvas {
  max-width: 100%;
  max-height: 100%;
}
</style>
