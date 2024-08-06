<template>

  <div class="app-container">
    <div>
      <qrcode-stream
        :constraints="selectedConstraints"
        :track="trackFunctionSelected.value"
        :formats="selectedBarcodeFormats"
        @error="onError"
        @detect="onDetect"
        @camera-on="onCameraReady"
      />
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      result: '',
      selectedConstraints: { facingMode: 'environment' },
      constraintOptions: [
        { label: 'rear camera', constraints: { facingMode: 'environment' } },
        { label: 'front camera', constraints: { facingMode: 'user' } }
      ],
      trackFunctionOptions: [
        { text: 'nothing (default)', value: undefined },
        { text: 'outline', value: this.paintOutline },
        { text: 'centered text', value: this.paintCenterText },
        { text: 'bounding box', value: this.paintBoundingBox }
      ],
      trackFunctionSelected: { text: 'outline', value: this.paintOutline },
      barcodeFormats: {
        aztec: false,
        code_128: true,
        code_39: false,
        code_93: false,
        codabar: false,
        databar: false,
        databar_expanded: false,
        data_matrix: false,
        dx_film_edge: false,
        ean_13: false,
        ean_8: false,
        itf: false,
        maxi_code: false,
        micro_qr_code: false,
        pdf417: false,
        qr_code: true,
        rm_qr_code: false,
        upc_a: false,
        upc_e: false,
        linear_codes: false,
        matrix_codes: false
      },
      error: ''
    }
  },
  computed: {
    selectedBarcodeFormats() {
      return Object.keys(this.barcodeFormats).filter(format => this.barcodeFormats[format])
    }
  },
  mounted() {
      navigator.mediaDevices.getUserMedia({ video: true })
      .then(stream => {
          // Camera được cấp quyền truy cập
          console.log('Camera access granted');
          // Ngừng stream khi không còn cần thiết
          stream.getTracks().forEach(track => track.stop());
      })
      .catch(err => {
          // Không thể truy cập camera
          console.error('Camera access denied:', err);
      });
  },
  created(){
      navigator.mediaDevices.getUserMedia({ video: true })
      .then(stream => {
          // Camera được cấp quyền truy cập
          console.log('Camera access granted');
          // Ngừng stream khi không còn cần thiết
          stream.getTracks().forEach(track => track.stop());
      })
      .catch(err => {
          // Không thể truy cập camera
          console.error('Camera access denied:', err);
      });
  },
  methods: {
    async onCameraReady() {
      try {
        const devices = await navigator.mediaDevices.enumerateDevices()
        const videoDevices = devices.filter(({ kind }) => kind === 'videoinput')

        this.constraintOptions = [
          ...[
            { label: 'rear camera', constraints: { facingMode: 'environment' } },
            { label: 'front camera', constraints: { facingMode: 'user' } }
          ],
          ...videoDevices.map(({ deviceId, label }) => ({
            label: `${label} (ID: ${deviceId})`,
            constraints: { deviceId }
          }))
        ]

        this.error = ''
      } catch (err) {
        this.onError(err)
      }
    },
    onDetect(detectedCodes) {
      detectedCodes.then(result =>{
          console.log("detectedCodes: ",result.content);
          this.$router.push({
              name: 'swipe-card',
              params: { content: result.content }

          })
      })
    },
    paintOutline(detectedCodes, ctx) {
      for (const detectedCode of detectedCodes) {
        const [firstPoint, ...otherPoints] = detectedCode.cornerPoints

        ctx.strokeStyle = 'red'

        ctx.beginPath()
        ctx.moveTo(firstPoint.x, firstPoint.y)
        for (const { x, y } of otherPoints) {
          ctx.lineTo(x, y)
        }
        ctx.lineTo(firstPoint.x, firstPoint.y)
        ctx.closePath()
        ctx.stroke()
      }
    },
    paintBoundingBox(detectedCodes, ctx) {
      for (const detectedCode of detectedCodes) {
        const { boundingBox: { x, y, width, height } } = detectedCode

        ctx.lineWidth = 2
        ctx.strokeStyle = '#007bff'
        ctx.strokeRect(x, y, width, height)
      }
    },
    paintCenterText(detectedCodes, ctx) {
      for (const detectedCode of detectedCodes) {
        const { boundingBox, rawValue } = detectedCode

        const centerX = boundingBox.x + boundingBox.width / 2
        const centerY = boundingBox.y + boundingBox.height / 2

        const fontSize = Math.max(12, (50 * boundingBox.width) / ctx.canvas.width)

        ctx.font = `bold ${fontSize}px sans-serif`
        ctx.textAlign = 'center'

        ctx.lineWidth = 3
        ctx.strokeStyle = '#35495e'
        ctx.strokeText(detectedCode.rawValue, centerX, centerY)

        ctx.fillStyle = '#5cb984'
        ctx.fillText(rawValue, centerX, centerY)
      }
    },
    onError(err) {
      this.error = `[${err.name}]: `

      if (err.name === 'NotAllowedError') {
        this.error += 'you need to grant camera access permission'
      } else if (err.name === 'NotFoundError') {
        this.error += 'no camera on this device'
      } else if (err.name === 'NotSupportedError') {
        this.error += 'secure context required (HTTPS, localhost)'
      } else if (err.name === 'NotReadableError') {
        this.error += 'is the camera already in use?'
      } else if (err.name === 'OverconstrainedError') {
        this.error += 'installed cameras are not suitable'
      } else if (err.name === 'StreamApiNotSupportedError') {
        this.error += 'Stream API is not supported in this browser'
      } else if (err.name === 'InsecureContextError') {
        this.error += 'Camera access is only permitted in secure context. Use HTTPS or localhost rather than HTTP.'
      } else {
        this.error += err.message
      }
    }
  }
}
</script>

<style scoped>
.error {
  font-weight: bold;
  color: red;
}
.barcode-format-checkbox {
  margin-right: 10px;
  white-space: nowrap;
  display: inline-block;
}
</style>
