function base64toBlod (base) {
  base = base.replace(/[\n\r]/g, '')
  const raw = window.atob(base)
  const rawLength = raw.length
  const uInt8Array = new Uint8Array(rawLength)
  for (let i = 0; i < rawLength; ++i) {
    uInt8Array[i] = raw.charCodeAt(i)
  }
  return new Blob([uInt8Array], { type: 'application/pdf' })
}

export {
  base64toBlod
}
