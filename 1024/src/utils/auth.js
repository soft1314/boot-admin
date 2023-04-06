import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'
const SeedKey = 'Admin-Seed'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function getSeed() {
  return Cookies.get(SeedKey)
}

export function setSeed(seed) {
  return Cookies.set(SeedKey, seed)
}

export function removeSeed() {
  return Cookies.remove(SeedKey)
}